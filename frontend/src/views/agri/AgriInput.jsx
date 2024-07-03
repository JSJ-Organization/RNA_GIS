import React from 'react'
import Input from '../../components/Input'
import { Helmet } from 'react-helmet-async'

const AgriInput = () => {
  return (
    <>
        <Helmet>
            <title>농기계 임대 검색</title>
        </Helmet>
        <Input />
    </>
  )
}

export default AgriInput
