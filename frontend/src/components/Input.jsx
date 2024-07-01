import { useRef, useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

const Input = () => {
  const [formVisible, setFormVisible] = useState(false);
  const [address, setAddress] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [results, setResults] = useState([]); // 검색 결과 상태 추가
  const inputRef = useRef(null);
  const buttonRef = useRef(null);

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

  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && buttonRef.current && !isLoading) {
      sendAddress();
    }
  };

  const sendAddress = async () => {
    if (inputRef.current) {
      const addressValue = inputRef.current.value;
      setAddress(addressValue);
      setIsLoading(true); // 로딩 시작
      setResults([]); // 검색 결과 초기화
      try {
        const tempUrl = `https://api.instantwebtools.net/v1/airlines`;
        const response = await fetch(tempUrl);
        const data = await response.json();
        console.log(data);
        setResults(data.slice(0, 5)); // 데이터 배열의 첫 다섯 항목을 설정
        timeoutFocus(inputRef);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setIsLoading(false); // 로딩 종료
      }
    }
  };

  const findResult = (index) => {
    console.log("Selected result index:", index);
    // 여기에 필요한 로직 추가
  };

  return (
    <div id="input-top-container">
      <div className={`c-form-container ${formVisible ? 'form-visible' : ''}`}>
        <div className="c-form">
          <input
            className="c-form-input"
            placeholder="주소입력"
            type="text"
            required
            ref={inputRef}
            onKeyDown={handleKeyPress}
            disabled={isLoading} // 로딩 중일 때 입력 비활성화
          />
          <div className="c-form-button-label" onClick={sendAddress}>
            <button
              className="c-form-button"
              type="button"
              ref={buttonRef}
              disabled={isLoading} // 로딩 중일 때 버튼 비활성화
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
                <div key={index} className='dropdown-item' onClick={() => findResult(index)}>
                  <li>{result.name}</li>
                  <div className='search-icon'><FontAwesomeIcon icon={faSearch} /></div>
                </div>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default Input;
