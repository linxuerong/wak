import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView_Trc.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/pageEth',
    name: 'pageEth',

    component: () => import('@/views/HomeView.vue')
  },
  {
    path: '/noWallet',
    name: 'noWallet',

    component: () => import('@/views/noWallet.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
