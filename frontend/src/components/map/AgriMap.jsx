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

  const mapRef = useRef(null);

  const getAgriData = async (distance) => {
    try {
      showLoading();
      const url = `http://localhost:8080/api/v1/search/office?query=s&x=${x}&y=${y}&distance=${distance}`;
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      setRadius(distance / 2);
      console.log(data);
    } catch (error) {
      console.log(error);
    } finally {
      hideLoading();
    }
  };

  useEffect(() => {
    getAgriData(2000);
  }, [x, y]);

  useEffect(() => {
    if (radius === 0) return;

    // Vworld 지도 기본 레이어 api 호출
    const vworldLayer = new TileLayer({
      source: new XYZ({
        url: `http://localhost:8080/api/v1/search/wmts/Base/{z}/{y}/{x}.png`,
      }),
    });

    // 마커 생성
    const marker = new Feature({
      geometry: new Point(fromLonLat([x, y])),
    });

    // 마커 스타일 설정
    marker.setStyle(
      new Style({
        image: new Icon({
          src: markerImg,
          scale: 1,
          anchor: [0.5, 1],
          anchorXUnits: 'fraction',
          anchorYUnits: 'fraction',
        }),
      })
    );

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
    const vectorSource = new VectorSource({
      features: [marker],
    });

    const markerLayer = new VectorLayer({
      title: '내 위치 마커',
      source: vectorSource,
    });

    const circleSource = new VectorSource({
      features: [circle],
    });

    const circleLayer = new VectorLayer({
      title: '반경 표시',
      source: circleSource,
    });

    // 마커 그룹 생성
    const markerGroup = new Group({
      title: '마커',
      layers: [markerLayer],
    });

    // 반경 그룹 생성
    const circleGroup = new Group({
      title: '반경',
      layers: [circleLayer],
    });

    // 지도 뷰 설정
    const view = new View({
      center: fromLonLat([x, y]),
      zoom: 16,
    });

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
      layers: [vworldLayer, markerGroup, circleGroup],
      view: view,
    });

    map.setTarget(mapRef.current || '');

    return () => {
      map.setTarget('');
    };
  }, [radius, x, y]);

  return (
    <div>
      <div id='map' ref={mapRef} />
    </div>
  );
};

export default AgriMap;
