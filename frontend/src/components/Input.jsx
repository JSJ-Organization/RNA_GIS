import { useRef, useState, useEffect } from 'react';

const Input = () => {
  const [formVisible, setFormVisible] = useState(false);
  const inputRef = useRef(null);
  const buttonRef = useRef(null);

  // 라벨 클릭 시 input 포커스 및 폼 표시
  const handleLabelClick = () => {
    setFormVisible(true);
  };

  useEffect(() => {
    if (formVisible) {
      setTimeout(() => {
        if (inputRef.current) {
          inputRef.current.focus();
        }
      }, 200);
    }
  }, [formVisible]);

  // input에서 엔터 클릭 시 버튼 클릭
  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && buttonRef.current) {
      sendAddress();
    }
  };

  // 주소 정보 전송
  const sendAddress = () => {
    if (inputRef.current) {
      console.log(inputRef.current.value);
    }
  };

  return (
    <div id="top-container">
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
