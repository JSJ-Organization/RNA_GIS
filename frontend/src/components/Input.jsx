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

  const tempData = {"response" : {"service" : {"name" : "search", "version" : "2.0", "operation" : "search", "time" : "47(ms)"}, "status" : "OK", "record" : {"total" : "224851", "current" : "10"}, "page" : {"total" : "22486", "current" : "1", "size" : "10"}, "result" : {"crs" : "EPSG:4326", "type" : "ADDRESS", "items" : [{"id" : "2914010600104150055", "address" : {"zipcode" : "61932", "category" : "ROAD", "road" : "광주광역시 서구 죽봉대로78번길 19 (농성동,광주 서구 농성동 주상복합)", "parcel" : "농성동 415-55", "bldnm" : "광주 서구 농성동 주상복합", "bldnmdc" : "더 리미티드 광주"}, "point" : {"x" : "126.885526898", "y" : "35.160759047"}}, {"id" : "2917010700102390002", "address" : {"zipcode" : "61187", "category" : "ROAD", "road" : "광주광역시 북구 우치로 77 (용봉동)", "parcel" : "용봉동 239-2", "bldnm" : "광주광역시 북구청", "bldnmdc" : ""}, "point" : {"x" : "126.912124376", "y" : "35.174290542"}}, {"id" : "2914010600102990000", "address" : {"zipcode" : "61928", "category" : "ROAD", "road" : "광주광역시 서구 경열로 33 (농성동)", "parcel" : "농성동 299", "bldnm" : "광주광역시 서구청", "bldnmdc" : ""}, "point" : {"x" : "126.890274956", "y" : "35.151969656"}}, {"id" : "4161010200105030000", "address" : {"zipcode" : "12791", "category" : "ROAD", "road" : "경기도 광주시 경충대로1461번길 43 (쌍령동,광주 센트럴 푸르지오)", "parcel" : "쌍령동 503", "bldnm" : "광주 센트럴 푸르지오", "bldnmdc" : "101동"}, "point" : {"x" : "127.270193785", "y" : "37.398515166"}}, {"id" : "4161010200105030000", "address" : {"zipcode" : "12791", "category" : "ROAD", "road" : "경기도 광주시 경충대로1461번길 43 (쌍령동,광주 센트럴 푸르지오)", "parcel" : "쌍령동 503", "bldnm" : "광주 센트럴 푸르지오", "bldnmdc" : "102동"}, "point" : {"x" : "127.269790211", "y" : "37.398016015"}}, {"id" : "4161010200105030000", "address" : {"zipcode" : "12791", "category" : "ROAD", "road" : "경기도 광주시 경충대로1461번길 43 (쌍령동,광주 센트럴 푸르지오)", "parcel" : "쌍령동 503", "bldnm" : "광주 센트럴 푸르지오", "bldnmdc" : "103동"}, "point" : {"x" : "127.269459407", "y" : "37.397404530"}}, {"id" : "4161010200105030000", "address" : {"zipcode" : "12791", "category" : "ROAD", "road" : "경기도 광주시 경충대로1461번길 43 (쌍령동,광주 센트럴 푸르지오)", "parcel" : "쌍령동 503", "bldnm" : "광주 센트럴 푸르지오", "bldnmdc" : "104동"}, "point" : {"x" : "127.269447271", "y" : "37.396737244"}}, {"id" : "4161010200105030000", "address" : {"zipcode" : "12791", "category" : "ROAD", "road" : "경기도 광주시 경충대로1461번길 43 (쌍령동,광주 센트럴 푸르지오)", "parcel" : "쌍령동 503", "bldnm" : "광주 센트럴 푸르지오", "bldnmdc" : "105동"}, "point" : {"x" : "127.268702392", "y" : "37.396925405"}}, {"id" : "4161010200105030000", "address" : {"zipcode" : "12791", "category" : "ROAD", "road" : "경기도 광주시 경충대로1461번길 43 (쌍령동,광주 센트럴 푸르지오)", "parcel" : "쌍령동 503", "bldnm" : "광주 센트럴 푸르지오", "bldnmdc" : "106동"}, "point" : {"x" : "127.267934653", "y" : "37.396878642"}}, {"id" : "4161010200105030000", "address" : {"zipcode" : "12791", "category" : "ROAD", "road" : "경기도 광주시 경충대로1461번길 43 (쌍령동,광주 센트럴 푸르지오)", "parcel" : "쌍령동 503", "bldnm" : "광주 센트럴 푸르지오", "bldnmdc" : "107동"}, "point" : {"x" : "127.267962471", "y" : "37.396260851"}}]}}}

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
        setResults(tempData.response.result.items.slice(0, 3));
        console.log(tempData.response.result.items);
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
    console.log(id);
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
                    <div className='dropdown-text'>
                      <li>{result.address.road}</li>
                      <li>{result.address.parcel}</li>
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
        {selectedResult && (
          <div>
            <div onClick={closeModal} className="modal-close">Close</div>
            <div className='address'>
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.address.road)}
              >
                {copiedSpan === selectedResult.address.road ? <>copy <FontAwesomeIcon icon={faThumbsUp} /></> : selectedResult.address.road}
              </span>
            </div>
            <div>
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.address.parcel)}
              >
                {copiedSpan === selectedResult.address.parcel ? <>copy <FontAwesomeIcon icon={faThumbsUp} /></> : selectedResult.address.parcel}
              </span>
            </div>
            <div>우편 번호 :&nbsp;
              <span
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.address.zipcode)}
              >
                {copiedSpan === selectedResult.address.zipcode ? <>copy <FontAwesomeIcon icon={faThumbsUp} /></> : selectedResult.address.zipcode}
              </span>
              </div>
            <div>
              위도 :&nbsp;
              <span 
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.point.x)}
              >
                {copiedSpan === selectedResult.point.x ? <>copy <FontAwesomeIcon icon={faThumbsUp} /></> : selectedResult.point.x}
              </span>
            </div>
            <div>
              경도 :&nbsp;
              <span 
                className='copy-span' 
                onClick={(e) => handleCopyClick(e, selectedResult.point.y)}
              >
                {copiedSpan === selectedResult.point.y ? <>copy <FontAwesomeIcon icon={faThumbsUp} /></> : selectedResult.point.y}
              </span>
            </div>
            <Link to="/coordinate/map">
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
