import React from 'react';
import { Link } from 'react-router-dom';
import krMap from '../assets/images/kr_map.png';

const Main = () => {
  return (
    <div id='main-top-container'>
      <div className='img-container'>
        <img className='kr-map' src={krMap} alt="벡터 한국 지도" />
      </div>
      <div className='btn-container'>
        <div className='wrapper'>
          <div className='row'>
            <Link to='/coordinate'>
              <div className='button-container'>
                <button className="btn red"><span>좌표 변환기</span></button>
              </div>
            </Link>
            <Link to='/agricultural'>
              <div className='button-container'>
                <button className="btn green"><span>농기계 임대 검색</span></button>
              </div>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Main;
