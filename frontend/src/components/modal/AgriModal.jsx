import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMapLocationDot } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { useState, useRef, useEffect } from 'react';

const AgriModal = ({ modalVisible, selectedResult, closeModal }) => {

  const [showHasAgri, setShowHasAgri] = useState(false);
  const [agriHeight, setAgriHeight] = useState('0px');
  const agriContentRef = useRef(null);

  const toggleHasAgri = () => {
    setShowHasAgri(!showHasAgri);
  };

  useEffect(() => {
    setAgriHeight(showHasAgri ? `${agriContentRef.current.scrollHeight}px` : '0px');
  }, [showHasAgri]);

  return (
    <div>
      <div onClick={closeModal} className={`modal-window ${modalVisible ? 'modal-visible' : ''}`}>
        {selectedResult && (
          <div>
            <div onClick={closeModal} className="modal-close">Close</div>
            <div className='address'>
              <span>
                {selectedResult.institutionNm}
              </span>
            </div>
            <div>
              <span>
                전화번호 : {selectedResult.phoneNumber}
              </span>
            </div>
            <div className='toggle-button' onClick={toggleHasAgri}>
              <span >보유 농기계 정보 {showHasAgri? '▲' : '▼' }</span>
            </div>
            <div 
              ref={agriContentRef} 
              className="has-agri-container" 
              style={{ height: agriHeight, transition: 'height 0.3s ease' }}>
              <div>
                <span>
                  트랙터 : {selectedResult.trctorHoldCo} 대
                </span>
              </div>
              <div>
                <span>
                  경운기 : {selectedResult.cultvtHoldCo} 대
                </span>
              </div>
              <div>
                <span>
                  관리기 : {selectedResult.manageHoldCo} 대
                </span>
              </div>
              <div>
                <span>
                  땅속 작물 수확기 : {selectedResult.harvestHoldCo} 대
                </span>
              </div>
              <div>
                <span>
                  탈곡기 및 정선 작업기 : {selectedResult.thresherHoldCo} 대
                </span>
              </div>
              <div>
                <span>
                  자주형 파종기 : {selectedResult.planterHoldCo} 대
                </span>
              </div>
              <div>
                <span>
                  이앙 작업기 : {selectedResult.planterHoldCo} 대
                </span>
              </div>
              <div>
                <span>
                  벼 수확 및 운반 작업기 : {selectedResult.planterHoldCo} 대
                </span>
              </div>
              <div>
                <span>
                  기타 임대 농기계 : {selectedResult.planterHoldCo} 대
                </span>
              </div>
            </div>
            <Link to={`/agricultural/map?x=${selectedResult.x}&y=${selectedResult.y}`}>
              <div className='map-link'>
                <FontAwesomeIcon icon={faMapLocationDot} /> 주변 농기계 임대 정보 보기
              </div>
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}

AgriModal.propTypes = {
  modalVisible: PropTypes.bool.isRequired,
  selectedResult: PropTypes.shape({
    institutionNm: PropTypes.string,
    phoneNumber: PropTypes.string,
    trctorHoldCo: PropTypes.string,
    cultvtHoldCo: PropTypes.string,
    manageHoldCo: PropTypes.string,
    harvestHoldCo: PropTypes.string,
    thresherHoldCo: PropTypes.string,
    planterHoldCo: PropTypes.string,
    x: PropTypes.number,
    y: PropTypes.number,
  }),
  closeModal: PropTypes.func.isRequired,
};

export default AgriModal;
