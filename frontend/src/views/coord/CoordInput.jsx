import React from 'react'
import Input from '../../components/Input'
import useCssVariables from '../../hooks/useCssVariables';
import PageMetadata from '../../components/PageMetadata';

const CoordInput = () => {

    useCssVariables({
      '--main-color-100': '#ff7b73',
      '--main-color-200': '#ffaea9',
      '--main-color-300': '#ffcccc',
    });

    const metaData = {
      title: "좌표 변환기",
      description : "좌표 변환기",
    }
  
    return (
      <>
        <PageMetadata meta={metaData} />
        <Input title={metaData.title} />
      </>
    )
}

export default CoordInput
