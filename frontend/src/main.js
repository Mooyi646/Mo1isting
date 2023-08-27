import './assets/main.css'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import App from './App.vue'
import router from './router'
import { createVuestic } from "vuestic-ui";
import "vuestic-ui/css";
import NaiveUI from 'naive-ui'
import { DatePicker } from 'ant-design-vue';

const app = createApp(App)

app.use(router)
app.use(ElementPlus).use(createVuestic()).use(NaiveUI).use(DatePicker)

app.mount('#app')
