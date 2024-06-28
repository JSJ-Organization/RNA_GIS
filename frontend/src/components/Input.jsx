
const Input = () => {
  return (
    <div id="input_container">
        <input className="c-checkbox" type="checkbox" id="checkbox" />
        <div className="c-formContainer">
            <div className="c-form">
                <input className="c-form__input" placeholder="주소입력" type="text" required />
                <label className="c-form__buttonLabel" htmlFor="checkbox">
                  <button className="c-form__button" type="button">검색</button>
                </label>
                <label className="c-form__toggle" htmlFor="checkbox" data-title="좌표 변환기 👆"></label>
            </div>
        </div> 
    </div>
  )
}

export default Input
