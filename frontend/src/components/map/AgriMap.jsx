import { useLocation } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';
import { Map as OlMap, View } from 'ol';
import { fromLonLat } from 'ol/proj';
import TileLayer from 'ol/layer/Tile';
import { XYZ } from 'ol/source';
import { defaults as defaultControls } from 'ol/control';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import Circle from 'ol/geom/Circle';
import { Style, Icon, Fill, Stroke } from 'ol/style';
import LayerSwitcher from 'ol-layerswitcher';
import Group from 'ol/layer/Group';
import Overlay from 'ol/Overlay';
import 'ol-layerswitcher/src/ol-layerswitcher.css';
import markerImg from '../../assets/images/marker.png';
import { useLoading } from '../../contexts/LoadingContext';

const AgriMap = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const x = parseFloat(queryParams.get('x'));
  const y = parseFloat(queryParams.get('y'));

  const { showLoading, hideLoading } = useLoading();
  const [radius, setRadius] = useState(0);
  const [rentInfoMarkers, setRentInfoMarkers] = useState([]);
  const [popupContent, setPopupContent] = useState(null);
  const mapRef = useRef(null);
  const popupRef = useRef(null);
  const viewRef = useRef(null);

  const getAgriData = async (distance) => {
    try {
      showLoading();
      const url = `https://${import.meta.env.VITE_DOMAIN}/api/v1/search/office?query=s&x=${x}&y=${y}&distance=${distance}`;
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      setRadius(distance);

      const markers = data.frcnRentInfoResponses.map(item => {
        const feature = new Feature({
          geometry: new Point(fromLonLat([item.x, item.y])),
        });
        feature.setProperties(item);
        return feature;
      });

      setRentInfoMarkers(markers);
    } catch (error) {
      console.log(error);
    } finally {
      hideLoading();
    }
  };

  useEffect(() => {
    getAgriData(20000);
  }, [x, y]);

  useEffect(() => {
    if (radius === 0) return;

    const vworldLayer = new TileLayer({
      source: new XYZ({
        url: `https://${import.meta.env.VITE_DOMAIN}/api/v1/search/wmts/Base/{z}/{y}/{x}.png`,
      }),
    });

    const circle = new Feature({
      geometry: new Circle(fromLonLat([x, y]), radius),
    });

    circle.setStyle(
      new Style({
        stroke: new Stroke({
          color: 'rgba(0, 0, 255, 0.5)',
          width: 2,
        }),
        fill: new Fill({
          color: 'rgba(0, 0, 255, 0.1)',
        }),
      })
    );

    const circleSource = new VectorSource({
      features: [circle],
    });

    const circleLayer = new VectorLayer({
      source: circleSource,
    });

    const rentInfoLayer = new VectorLayer({
      title: '전체',
      type: 'base',
      name: 'agri',
      visible: true,
      source: new VectorSource({
        features: rentInfoMarkers,
      }),
      style: new Style({
        image: new Icon({
          src: markerImg,
          scale: 1,
          anchor: [0.5, 1],
          anchorXUnits: 'fraction',
          anchorYUnits: 'fraction',
        }),
      }),
    });

    const createFilteredLayer = (title, filterKey) => {
      const filteredFeatures = rentInfoMarkers.filter(feature => parseInt(feature.get(filterKey)) > 0);
      return new VectorLayer({
        title,
        type: 'base',
        visible: false,
        source: new VectorSource({
          features: filteredFeatures,
        }),
        style: new Style({
          image: new Icon({
            src: markerImg,
            scale: 1,
            anchor: [0.5, 1],
            anchorXUnits: 'fraction',
            anchorYUnits: 'fraction',
          }),
        }),
      });
    };

    const trctorLayer = createFilteredLayer('트랙터', 'trctorHoldCo');
    const thresherLayer = createFilteredLayer('탈곡기', 'thresherHoldCo');
    const transplantLayer = createFilteredLayer('이앙 작업기', 'transplantHoldCo');
    const cultvtLayer = createFilteredLayer('경운기', 'cultvtHoldCo');
    const manageLayer = createFilteredLayer('관리기', 'manageHoldCo');
    const harvestLayer = createFilteredLayer('땅속 작물 수확기', 'harvestHoldCo');
    const planterLayer = createFilteredLayer('자주형 파종기', 'planterHoldCo');
    const rcepntLayer = createFilteredLayer('벼 수확 및 운반 작업기', 'rcepntHoldCo');
    const etcRentLayer = createFilteredLayer('기타 임대 농기계', 'etcRentHoldCo ');

    const rentInfoGroup = new Group({
      title: '대여 가능 농기계 종류',
      layers: [ etcRentLayer, transplantLayer,cultvtLayer,manageLayer,harvestLayer,planterLayer,rcepntLayer, thresherLayer, trctorLayer, rentInfoLayer],
    });


    const view = new View({
      center: fromLonLat([x, y]),
      zoom: 11,
    });
    viewRef.current = view;

    const layerSwitcher = new LayerSwitcher({
      activationMode: 'click',
      startActive: true,
      tipLabel: 'Layers',
      groupSelectStyle: 'children',
    });

    const map = new OlMap({
      controls: defaultControls().extend([layerSwitcher]),
      layers: [vworldLayer, rentInfoGroup, circleLayer],
      view: view,
    });

    const popup = new Overlay({
      element: popupRef.current,
      positioning: 'bottom-center',
      stopEvent: false,
      offset: [0, -50],
    });
    map.addOverlay(popup);

    map.on('click', function(evt) {
      map.forEachFeatureAtPixel(evt.pixel, function(feature) {
        if (feature.getGeometry().getType() === 'Point' && feature.get('x') && feature.get('y')) {
          const coordinates = feature.getGeometry().getCoordinates();
          const properties = feature.getProperties();
          setPopupContent(properties);
          popup.setPosition(coordinates);
          viewRef.current.animate({ center: coordinates, duration: 500 });
        }
      });
    });

    map.setTarget(mapRef.current || '');

    return () => {
      map.setTarget('');
    };
  }, [radius, x, y, rentInfoMarkers]);

  return (
    <div>
      <div id='map' ref={mapRef} />
      <div ref={popupRef} className="ol-popup">
        {popupContent && (
          <div>
            <h3>{popupContent.officeNm}</h3>
            <p>{popupContent.phoneNumber}</p>
            {/* 추가 정보 표시 */}
          </div>
        )}
      </div>
    </div>
  );
};

export default AgriMap;
