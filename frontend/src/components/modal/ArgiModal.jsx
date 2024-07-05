import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faThumbsUp, faMapLocationDot } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const AgriModal = ({ modalVisible, selectedResult, closeModal }) => {
  
    const [copiedSpan, setCopiedSpan] = useState(null);
    const copyToClipboard = async (text) => {
        try {
          await navigator.clipboard.writeText(text);
          console.log('Text copied to clipboard');
        } catch (err) {
          console.error('Error copying text: ', err);
        }
      };
    
      const handleCopyClick = async (e, value) => {
        if (copiedSpan !== null) {
          return;
        }
        setCopiedSpan(value);
        await copyToClipboard(value);
        setTimeout(() => {
          setCopiedSpan(null);
        }, 1000);
      };
    return (
    <div>
      <div onClick={closeModal} className={`modal-window ${modalVisible ? 'modal-visible' : ''}`}>
        {selectedResult && (
          <div>
            <div onClick={closeModal} className="modal-close">Close</div>
            <div className='address'>
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.roadNameAddress)}
              >
                {copiedSpan === selectedResult.roadNameAddress ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.roadNameAddress}
              </span>
            </div>
            <div>
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.parcelAddress)}
              >
                {copiedSpan === selectedResult.parcelAddress ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.parcelAddress}
              </span>
            </div>
            <div>우편 번호 :&nbsp;
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.zipcode)}
              >
                {copiedSpan === selectedResult.zipcode ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.zipcode}
              </span>
            </div>
            <div>
              위도 :&nbsp;
              <span 
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.x)}
              >
                {copiedSpan === selectedResult.x ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.x}
              </span>
            </div>
            <div>
              경도 :&nbsp;
              <span 
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.y)}
              >
                {copiedSpan === selectedResult.y ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.y}
              </span>
            </div>
            <Link to={`/coordinate/map?x=${selectedResult.x}&y=${selectedResult.y}`}>
              <div className='map-Link'>
                <FontAwesomeIcon icon={faMapLocationDot} /> 위치 보기
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
