import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import Web3 from 'web3'
import 'element-ui/lib/theme-chalk/index.css';
Vue.prototype.Web3 = Web3



Vue.prototype.$downFun = {
  msg: 0
};

Vue.config.productionTip = false
Vue.use(ElementUI)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app') 
