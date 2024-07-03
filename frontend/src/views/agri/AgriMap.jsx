import React from 'react'
import Map from '../../components/Map'
import { Helmet } from 'react-helmet-async'

const AgriMap = () => {
  return (
    <>
        <Helmet>
            <title>농기계 임대 검색 - 지도</title>
        </Helmet>
        <Map />
    </>
  )
}

export default AgriMap
