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

  // URLSearchParams를 사용하여 쿼리 파라미터를 파싱
  const queryParams = new URLSearchParams(location.search);
  const x = parseFloat(queryParams.get('x'));
  const y = parseFloat(queryParams.get('y'));

  const { showLoading, hideLoading } = useLoading();
  const [radius, setRadius] = useState(0);
  const [rentInfoMarkers, setRentInfoMarkers] = useState([]);
  const [popupContent, setPopupContent] = useState(null);
  const mapRef = useRef(null);
  const popupRef = useRef(null);
  const viewRef = useRef(null);  // View를 참조하기 위한 ref 추가

  const getAgriData = async (distance) => {
    try {
      showLoading();
      const url = `http://localhost:8080/api/v1/search/office?query=s&x=${x}&y=${y}&distance=${distance}`;
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      setRadius(distance);

      // Rent Info Markers 생성
      const markers = data.frcnRentInfoResponses.map(item => {
        const feature = new Feature({
          geometry: new Point(fromLonLat([item.x, item.y])),
        });
        feature.setProperties(item);
        return feature;
      });

      setRentInfoMarkers(markers);
      console.log(data);
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

    // Vworld 지도 기본 레이어 api 호출
    const vworldLayer = new TileLayer({
      source: new XYZ({
        url: `http://localhost:8080/api/v1/search/wmts/Base/{z}/{y}/{x}.png`,
      }),
    });

    // 원 생성 (반경 : raidus미터)
    const circle = new Feature({
      geometry: new Circle(fromLonLat([x, y]), radius),
    });

    // 원 스타일 설정
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

    // 벡터 소스 및 레이어 생성
    const circleSource = new VectorSource({
      features: [circle],
    });

    const circleLayer = new VectorLayer({
      title: '반경 표시',
      source: circleSource,
    });

    // Rent Info 벡터 소스 및 레이어 생성
    const rentInfoSource = new VectorSource({
      features: rentInfoMarkers,
    });

    const rentInfoLayer = new VectorLayer({
      title: '농기계 대여 정보',
      source: rentInfoSource,
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

    // 반경 그룹 생성
    const circleGroup = new Group({
      title: '반경',
      layers: [circleLayer],
    });

    // 농기계 대여 정보 그룹 생성
    const rentInfoGroup = new Group({
      title: '농기계 대여 정보',
      layers: [rentInfoLayer],
    });

    // 지도 뷰 설정
    const view = new View({
      center: fromLonLat([x, y]),
      zoom: 11,
    });
    viewRef.current = view;  // view를 ref에 저장

    // 레이어 스위처 생성
    const layerSwitcher = new LayerSwitcher({
      activationMode: 'click',
      startActive: true,
      tipLabel: 'Layers', // Optional label for button
      groupSelectStyle: 'children', // Can be 'children' [default], 'group' or 'none'
    });

    // 지도 생성
    const map = new OlMap({
      controls: defaultControls().extend([layerSwitcher]),
      layers: [vworldLayer, circleGroup, rentInfoGroup],
      view: view,
    });

    // 팝업 생성
    const popup = new Overlay({
      element: popupRef.current,
      positioning: 'bottom-center',
      stopEvent: false,
      offset: [0, -50],
    });
    map.addOverlay(popup);

    // 마커 클릭 이벤트 설정
    map.on('click', function(evt) {
      map.forEachFeatureAtPixel(evt.pixel, function(feature) {
        if (feature.getGeometry().getType() === 'Point' && feature.get('x') && feature.get('y')) {
          const coordinates = feature.getGeometry().getCoordinates();
          const properties = feature.getProperties();
          console.log(properties);
          setPopupContent(properties);
          popup.setPosition(coordinates);
          viewRef.current.animate({ center: coordinates, duration: 500 });  // 지도 중심 이동 애니메이션 추가
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
