import React from 'react';
import PropTypes from 'prop-types';
import { Helmet } from 'react-helmet-async';

const PageMetadata = ({ meta }) => {
    const {
        title, 
        description,
    } = meta;

    return (
        <Helmet>
            <title>{title}</title>
            <meta name="description" content={description} />
        </Helmet>
    );
}

PageMetadata.propTypes = {
    meta: PropTypes.shape({
      title: PropTypes.string,
      description: PropTypes.string,
    //   keywords: PropTypes.string,
    }).isRequired,
  };

export default PageMetadata;