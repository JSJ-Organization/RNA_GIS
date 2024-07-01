import React from 'react';
import { useLocation } from 'react-router-dom';

const Map = () => {
  const location = useLocation();

  // URLSearchParams를 사용하여 쿼리 파라미터를 파싱
  const queryParams = new URLSearchParams(location.search);
  const x = queryParams.get('x');
  const y = queryParams.get('y');

  return (
    <div>
      <h1>Map Coordinates</h1>
      <p>X: {x}</p>
      <p>Y: {y}</p>
    </div>
  );
}

export default Map;
