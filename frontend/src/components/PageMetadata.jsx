import { Helmet } from 'react-helmet-async';
import { usePath } from '../PathContext';
import { pathData } from '../pathData'

const PageMetadata = () => {

    const { id } = usePath();
    const metaData = pathData[id];

    return (
        <Helmet>
            <title>{metaData.title}</title>
            <meta name="description" content={metaData.description} />
        </Helmet>
    );
}

export default PageMetadata;