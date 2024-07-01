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
  const [selectedResult, setSelectedResult] = useState('');
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
      <div onClick={closeModal} className={`modal-window ${modalVisible ? 'modal-visible' : ''}`}>
        <div>
          <div onClick={closeModal} className="modal-close">Close</div>
          <div className='address'>{selectedResult.name}</div>
          <div>위도 : 127.00</div>
          <div>경도 : 127.00</div>
          <Link to="/coordinate/map"><div className='map-Link'><FontAwesomeIcon icon={faMapLocationDot} /> 위치 보기</div></Link>
        </div>
      </div>
    </>
  );
};

export default Input;
