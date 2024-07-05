import { useLocation } from 'react-router-dom';
import { useEffect, useRef } from 'react';
import { Map as OlMap, View } from 'ol';
import { fromLonLat } from 'ol/proj';
import TileLayer from 'ol/layer/Tile';
import { XYZ } from 'ol/source';
import { defaults as defaultControls } from 'ol/control';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import { Style, Icon } from 'ol/style';
import LayerSwitcher from 'ol-layerswitcher';
import 'ol-layerswitcher/src/ol-layerswitcher.css';
import markerImg from '../../assets/images/marker.png';

const AgriMap = () => {
  const location = useLocation();

  // URLSearchParams를 사용하여 쿼리 파라미터를 파싱
  const queryParams = new URLSearchParams(location.search);
  const x = parseFloat(queryParams.get('x'));
  const y = parseFloat(queryParams.get('y'));

  const mapRef = useRef(null);

  useEffect(() => {

    // Vworld 지도 기본 레이어 api 호출
    const vworldLayer  = new TileLayer({
      source: new XYZ({
        url : `http://localhost:8080/api/v1/search/wmts/Base/{z}/{y}/{x}.png`,
      })
    })

    // 마커 생성
    const marker = new Feature({
      geometry: new Point(fromLonLat([x, y]))
    });

    // 마커 스타일 설정
    marker.setStyle(new Style({
      image: new Icon({
        src: markerImg,
        scale: 1,
        anchor: [0.5, 1],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
      })
    }));

    // 벡터 소스 및 레이어 생성
    const vectorSource = new VectorSource({
      features: [marker]
    });

    const markerLayer = new VectorLayer({
      title: '마커',
      source: vectorSource
    });

    // 지도 뷰 설정
    const view = new View({
      center: fromLonLat([x, y]),
      zoom: 16
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
      layers: [vworldLayer, markerLayer],
      view: view
    });

    map.setTarget(mapRef.current || '');

    return () => {
      map.setTarget('');
    };
  }, [x, y]);

  return (
      <div id='map' ref={mapRef} />
  );
}

export default AgriMap;
