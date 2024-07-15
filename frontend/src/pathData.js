export const pathData = {
  'home' : {
    title : '주소인포',
    description: '주소를 활용한 모든 검색',
  },
  'coordinate' : {
    title : '좌표 변환기',
    description: '좌표 변환기',
    searchException: '검색 결과가 없습니다.',
    api: {
      input: `https://${import.meta.env.VITE_DOMAIN}/api/v1/search/api-point-with-page`,
    }
  },
  'agricultural' : {
    title : '농기계 임대 검색',
    description: '농기계 임대 검색',
    searchException: '해당 주소에 농기계가 없습니다.',
    api: {
      input: `https://${import.meta.env.VITE_DOMAIN}/api/v1/search/office-address`,
    }
  },
}