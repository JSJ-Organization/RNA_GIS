
import { useLoading } from '../contexts/LoadingContext';

const Loader = () => {

    const { isLoading } = useLoading();

    if (!isLoading) return null;

    return (
        <div className='loader-container'>
            <span className="loader"></span>
        </div>
    )
}

export default Loader
