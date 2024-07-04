import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  // server: { 개발 서버 설정
  //   host: '0.0.0.0',
  //   port: 80,
  //   strictPort: true,
  // },
})

