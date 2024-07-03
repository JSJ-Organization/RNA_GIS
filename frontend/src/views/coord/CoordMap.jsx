import React from 'react'
import Map from '../../components/Map'
import { Helmet } from 'react-helmet-async'

const CoordMap = () => {
  return (
    <>
        <Helmet>
            <title>좌표 변환기 - 지도</title>
        </Helmet>
        <Map />
    </>
  )
}

export default CoordMap
