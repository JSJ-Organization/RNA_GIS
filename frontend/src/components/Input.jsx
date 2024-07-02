import { useRef, useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch, faMapLocationDot, faHandPointer, faThumbsUp } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';

const Input = () => {
  const [formVisible, setFormVisible] = useState(false);
  const [address, setAddress] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [results, setResults] = useState([]); 
  const [modalVisible, setModalVisible] = useState(false); 
  const [selectedResult, setSelectedResult] = useState(null);
  const [copiedSpan, setCopiedSpan] = useState(null);
  const inputRef = useRef(null);
  const buttonRef = useRef(null);
  const containerRef = useRef(null);

  const handleWelcomeClick = () => {
    setFormVisible(true);
  };

  const timeoutFocus = (ref, time = 0) => {
    setTimeout(() => {
      ref.current.focus();
    }, time);
  };

  useEffect(() => {
    if (formVisible && inputRef.current) {
      timeoutFocus(inputRef, 200);
    }
  }, [formVisible]);

  useEffect(() => {
    if (results.length > 0) {
      centerContainer();
    }
  }, [results]);

  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && buttonRef.current && !isLoading) {
      sendAddress();
    }
  };

  const sendAddress = async () => {
    if (inputRef.current.value) {
      const addressValue = inputRef.current.value;
      setAddress(addressValue);
      setIsLoading(true);
      setResults([]);
      try {
        const tempUrl = `http://localhost:8080/api/v1/search/point-with-page?query=${addressValue}&page=1`;
        const response = await fetch(tempUrl);
        const data = await response.json();
        setResults(data.response.result.items);
        console.log(data.response.result.items);
        timeoutFocus(inputRef);
      } catch (error) {
        setResults([{id:0}]);
        timeoutFocus(inputRef);
        console.error('Error fetching data:', error);
      } finally {
        setIsLoading(false);
      }
    }
  };

  const centerContainer = () => {
    if (containerRef.current) {
      const container = containerRef.current;
      const dropdown = container.querySelector('.dropdown-position');

      if (dropdown) {
        const dropdownHeight = dropdown.offsetHeight;
        container.style.top = `-${dropdownHeight / 2 - 16}px`;
      }
    }
  };

  const findResult = (id) => {
    if(id === 0 ){
      return;
    }
    const result = results.find((item) => item.id === id);
    if (result) {
      setSelectedResult(result);
      setModalVisible(true);
    } else {
      console.log('Result not found');
    }
  };

  const closeModal = (e) => {
    if (e.target.classList.contains('modal-window') || e.target.classList.contains('modal-close')) {
      setModalVisible(false);
    }
  };

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
    <>
      <div id="input-top-container">
        <div ref={containerRef} className={`c-form-container ${formVisible ? 'form-visible' : ''}`}>
          <div className="c-form">
            <input
              className="c-form-input"
              placeholder="주소입력"
              type="text"
              required
              ref={inputRef}
              onKeyDown={handleKeyPress}
              disabled={isLoading}
            />
            <div className="c-form-button-label" onClick={sendAddress}>
              <button
                className="c-form-button"
                type="button"
                ref={buttonRef}
                disabled={isLoading}
              >
                {isLoading ? <div className="c-form-spinner"></div> : '검색'}
              </button>
            </div>
            <div
              className="c-form-welcome"
              onClick={handleWelcomeClick}
            >좌표 변환기 <span className='c-form-welcome-icon'><FontAwesomeIcon icon={faHandPointer} /></span></div>
          </div>
          {results.length > 0 && (
            <div className='dropdown-position'>
              <ul className='dropdown'>
              {results.map((result, index) => (
                  <div key={index} className='dropdown-item' onClick={() => findResult(result.id)}>
                    {result.id === 0 ? (
                      <div className='dropdown-text'>
                        <li>검색 결과가 없습니다</li>
                      </div>
                    ) : (
                      <>
                        <div className='dropdown-text'>
                          <li>{result.address.road}</li>
                          <li>{result.address.parcel}</li>
                        </div>
                        <div className='search-icon'><FontAwesomeIcon icon={faSearch} /></div>
                      </>
                    )}
                  </div>
                ))}

              </ul>
            </div>
          )}
        </div>
      </div>

      <div onClick={closeModal} className={`modal-window ${modalVisible ? 'modal-visible' : ''}`}>
        {selectedResult && (
          <div>
            <div onClick={closeModal} className="modal-close">Close</div>
            <div className='address'>
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.address.road)}
              >
                {copiedSpan === selectedResult.address.road ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.address.road}
              </span>
            </div>
            <div>
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.address.parcel)}
              >
                {copiedSpan === selectedResult.address.parcel ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.address.parcel}
              </span>
            </div>
            <div>우편 번호 :&nbsp;
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.address.zipcode)}
              >
                {copiedSpan === selectedResult.address.zipcode ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.address.zipcode}
              </span>
              </div>
            <div>
              위도 :&nbsp;
              <span 
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.point.x)}
              >
                {copiedSpan === selectedResult.point.x ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.point.x}
              </span>
            </div>
            <div>
              경도 :&nbsp;
              <span 
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.point.y)}
              >
                {copiedSpan === selectedResult.point.y ? <span className='copied-span'>copy <FontAwesomeIcon icon={faThumbsUp} /></span> : selectedResult.point.y}
              </span>
            </div>
            <Link to={`/coordinate/map?x=${selectedResult.point.x}&y=${selectedResult.point.y}`}>
              <div className='map-Link'>
                <FontAwesomeIcon icon={faMapLocationDot} /> 위치 보기
              </div>
            </Link>
          </div>
        )}
      </div>
    </>
  );
};

export default Input;
