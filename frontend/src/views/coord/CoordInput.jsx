import React from 'react'
import Input from '../../components/Input'
import { Helmet } from 'react-helmet-async'
import useCssVariables from '../../hooks/useCssVariables';

const CoordInput = () => {

    useCssVariables({
        '--coordinate100': '#ff7b73',
        '--coordinate200': '#ffaea9',
        '--coordinate300': '#ffcccc',
      });

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
