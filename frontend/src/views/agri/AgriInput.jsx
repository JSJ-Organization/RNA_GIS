import React from 'react'
import Input from '../../components/Input'
import useCssVariables from '../../hooks/useCssVariables'
import PageMetadata from '../../components/PageMetadata';

const AgriInput = () => {
    useCssVariables({
      '--main-color-100': '#00c94d',
      '--main-color-200': '#A8FFC9',
      '--main-color-300': '#6be299',
    });

    const metaData = {
      title: '농기계 임대 검색',
      description: '농기계 임대 검색',
    };

  return (
    <>
      <PageMetadata meta={metaData} />
      <Input title={metaData.title} />
    </>
  )
}

export default AgriInput
