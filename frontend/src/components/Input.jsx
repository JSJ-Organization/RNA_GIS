import { useRef, useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch, faMapLocationDot } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';

const Input = () => {
  const [formVisible, setFormVisible] = useState(false);
  const [address, setAddress] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [results, setResults] = useState([]); 
  const [modalVisible, setModalVisible] = useState(false); 
  const [selectedResult, setSelectedResult] = useState(null);
  const [copiedSpan, setCopiedSpan] = useState(null); // 복사 상태를 관리하는 상태 추가
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
    if (inputRef.current) {
      const addressValue = inputRef.current.value;
      setAddress(addressValue);
      setIsLoading(true);
      setResults([]);
      try {
        const tempUrl = `https://api.instantwebtools.net/v1/airlines`;
        const response = await fetch(tempUrl);
        const data = await response.json();
        console.log(data);
        setResults(data.slice(0, 3));
        timeoutFocus(inputRef);
      } catch (error) {
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
    const result = results.find((item) => item._id === id);
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
              data-title="좌표 변환기 👆"
              onClick={handleWelcomeClick}
            ></div>
          </div>
          {results.length > 0 && (
            <div className='dropdown-position'>
              <ul className='dropdown'>
                {results.map((result, index) => (
                  <div key={index} className='dropdown-item' onClick={() => findResult(result._id)}>
                    <div className='dropdown-text'>
                      <li>{result.logo}</li>
                      <li>{result.name}</li>
                    </div>
                    <div className='search-icon'><FontAwesomeIcon icon={faSearch} /></div>
                  </div>
                ))}
              </ul>
            </div>
          )}
        </div>
      </div>
      {selectedResult && (
        <div onClick={closeModal} className={`modal-window ${modalVisible ? 'modal-visible' : ''}`}>
          <div>
            <div onClick={closeModal} className="modal-close">Close</div>
            <div className='address'>{selectedResult.name}</div>
            <div>
              위도 :  
              <span 
                className='lat' 
                onClick={(e) => handleCopyClick(e, selectedResult.website)}
              >
                {copiedSpan === selectedResult.website ? 'copy 👌' : selectedResult.website}
              </span>
            </div>
            <div>
              경도 :  
              <span 
                className='lng' 
                onClick={(e) => handleCopyClick(e, selectedResult._id)}
              >
                {copiedSpan === selectedResult._id ? 'copy 👌' : selectedResult._id}
              </span>
            </div>
            <Link to="/coordinate/map">
              <div className='map-Link'>
                <FontAwesomeIcon icon={faMapLocationDot} /> 위치 보기
              </div>
            </Link>
          </div>
        </div>
      )}
    </>
  );
};

export default Input;
