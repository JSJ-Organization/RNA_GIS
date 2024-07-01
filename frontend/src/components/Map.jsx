import React from 'react';
import { useLocation } from 'react-router-dom';
import { useEffect, useRef } from 'react'
import { Map as OlMap, View } from 'ol';
import { fromLonLat } from 'ol/proj';
import TileLayer from 'ol/layer/Tile';
import { OSM } from 'ol/source';
import { defaults } from 'ol/control/defaults';


const Map = () => {
  const location = useLocation();

  // URLSearchParams를 사용하여 쿼리 파라미터를 파싱
  const queryParams = new URLSearchParams(location.search);
  const x = queryParams.get('x');
  const y = queryParams.get('y');

  const mapRef = useRef(null)
  useEffect(() => {
    const tilelayer = new TileLayer({
        source: new OSM({ attributions: '' })
    });
    const view = new View({
        center: fromLonLat([x, y]), // 서울시청 경도와 위도
        zoom: 16
    })
  
    const map = new OlMap({
        controls: defaults({ zoom: false, rotate: false, attribution: false }),
        layers: [
            tilelayer
        ],
        view: view,
    })
    map.setTarget(mapRef.current || '')
    return () => {
        map.setTarget('');
    };
}, []);
  return (
  <div id='map' ref={mapRef} />
)
}

export default Map;
