import React from 'react'
import Input from '../../components/Input'
import useCssVariables from '../../hooks/useCssVariables';
import PageMetadata from '../../components/PageMetadata';

const CoordInputView = () => {

    useCssVariables({
      '--main-color-100': '#ff7b73',
      '--main-color-200': '#ffaea9',
      '--main-color-300': '#ffcccc',
    });

    return (
      <>
        <PageMetadata />
        <Input />
      </>
    )
}

export default CoordInputView
