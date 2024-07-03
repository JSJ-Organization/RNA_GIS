import React from 'react'
import Input from '../../components/Input'
import { Helmet } from 'react-helmet-async'

const CoordInput = () => {
  return (
    <>
        <Helmet>
            <title>좌표 변환기</title>
        </Helmet>
        <Input /> 
    </>
  )
}

export default CoordInput
