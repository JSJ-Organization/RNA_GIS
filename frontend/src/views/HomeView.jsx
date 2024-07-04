import Main from '../components/Main'
import PageMetadata from '../components/PageMetadata';

const HomeView = () => {

  const metaData = {
    title: '주소 인포',
    description: '주소를 활용한 검색',
  };

  return (
    <>
      <PageMetadata meta={metaData} />
      <Main />
    </>
  )
}

export default HomeView
