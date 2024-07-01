import React from 'react';
import { useLocation } from 'react-router-dom';
import { useEffect, useRef } from 'react';
import { Map as OlMap, View } from 'ol';
import { fromLonLat } from 'ol/proj';
import TileLayer from 'ol/layer/Tile';
import { OSM } from 'ol/source';
import { defaults } from 'ol/control/defaults';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import { Style, Icon } from 'ol/style';

const Map = () => {
  const location = useLocation();

  // URLSearchParams를 사용하여 쿼리 파라미터를 파싱
  const queryParams = new URLSearchParams(location.search);
  const x = parseFloat(queryParams.get('x'));
  const y = parseFloat(queryParams.get('y'));

  const mapRef = useRef(null);

  useEffect(() => {
    // 지도 기본 레이어
    const tilelayer = new TileLayer({
      source: new OSM({ attributions: '' })
    });

    // 마커 생성
    const marker = new Feature({
      geometry: new Point(fromLonLat([x, y]))
    });

    // 마커 스타일 설정
    marker.setStyle(new Style({
      image: new Icon({
        src: 'https://openlayers.org/en/latest/examples/data/icon.png',
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
      source: vectorSource
    });

    // 지도 뷰 설정
    const view = new View({
      center: fromLonLat([x, y]),
      zoom: 16
    });

    // 지도 생성
    const map = new OlMap({
      controls: defaults({ zoom: false, rotate: false, attribution: false }),
      layers: [tilelayer, markerLayer],
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

export default Map;
