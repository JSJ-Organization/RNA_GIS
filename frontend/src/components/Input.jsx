import { useRef, useState, useEffect } from 'react';

const Input = () => {
  const [formVisible, setFormVisible] = useState(false);
  const [address, setAddress] = useState('');
  const inputRef = useRef(null);
  const buttonRef = useRef(null);

  // 라벨 클릭 시 input 포커스 및 폼 표시
  const handleLabelClick = () => {
    setFormVisible(true);
  };

  useEffect(() => {
    if (formVisible && inputRef.current) {
      inputRef.current.focus();
    }
  }, [formVisible]);

  // input에서 엔터 클릭 시 버튼 클릭
  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && buttonRef.current) {
      sendAddress();
    }
  };

  // 주소 정보 전송 및 API 호출
  const sendAddress = async () => {
    if (inputRef.current) {
      const addressValue = inputRef.current.value;
      setAddress(addressValue);
      try {
        const response = await fetch(`https://api.example.com/address?query=${encodeURIComponent(addressValue)}`);
        const data = await response.json();
        console.log(data);
        // 필요한 추가 처리
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }
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
          />
          <div className="c-form-button-label" onClick={sendAddress}>
            <button
              className="c-form-button"
              type="button"
              ref={buttonRef}
            >
              검색
            </button>
          </div>
          <div
            className="c-form-welcome"
            data-title="좌표 변환기 👆"
            onClick={handleLabelClick}
          ></div>
        </div>
      </div> 
    </div>
  );
};

export default Input;
