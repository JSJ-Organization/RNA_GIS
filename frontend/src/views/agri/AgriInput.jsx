import React from 'react'
import Input from '../../components/Input'
import { Helmet } from 'react-helmet-async'
import useCssVariables from '../../hooks/useCssVariables'

const AgriInput = () => {
    useCssVariables({
        '--coordinate100': '#1bff73',
        '--coordinate200': '#b0ffb4',
        '--coordinate300': '#A8FFC9',
      });

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
