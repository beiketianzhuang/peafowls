import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import routes from './router/router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store';
import axios from 'axios';
import env from './config/env'
// import Scheduler from './components/scheduler';


Vue.config.productionTip = false;
axios.defaults.baseURL = 'http://localhost:8082';


Vue.use(Vuex);
Vue.use(ElementUI);
Vue.use(VueRouter);



const router = new VueRouter({
  routes
});


new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
