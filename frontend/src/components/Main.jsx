import React from 'react'
import { Link } from 'react-router-dom';

const Main = () => {
  return (
    <div id='main-top-container'>
      <Link to='/coordinate'>
      <button className="btn"><span>좌표 변환기</span></button>
      </Link>
    </div>
  )
}

export default Main
