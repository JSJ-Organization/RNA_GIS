import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMapLocationDot } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const AgriModal = ({ modalVisible, selectedResult, closeModal }) => {
  
    return (
    <div>
      <div onClick={closeModal} className={`modal-window ${modalVisible ? 'modal-visible' : ''}`}>
        {selectedResult && (
          <div>
            <div onClick={closeModal} className="modal-close">Close</div>
            <div className='address'>
              <span>
                {selectedResult.roadNameAddress}
              </span>
            </div>
            <div>
              <span>
                {selectedResult.parcelAddress}
              </span>
            </div>
            <Link to={`/agricultural/map?x=${selectedResult.x}&y=${selectedResult.y}`}>
              <div className='map-Link'>
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
        roadNameAddress: PropTypes.string,
        parcelAddress: PropTypes.string,
        zipcode: PropTypes.number,
        x: PropTypes.number,
        y: PropTypes.number
    }),
    closeModal: PropTypes.func.isRequired
};


export default AgriModal;
