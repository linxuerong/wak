<template>
  <div id="app">

    <router-view />
  </div>
</template>
<script>

export default {
  components: {

  },
  data () {
    return {

    }
  },

}
</script>
<style>
body,
html {
  padding: 0;
  margin: 0;
  background-color: #fbfbfb;
}

li {
  list-style: none;
}

.flex {
  display: flex;
}

.jus-bet {
  display: flex;
  justify-content: space-between;
}

.jus-evn {
  display: flex;
  justify-content: space-evenly;
}

.jus-arn {
  display: flex;
  justify-content: space-around;
}

.f-warp {
  flex-wrap: wrap;
}

.al-item {
  display: flex;
  align-items: center;
}

.white {
  color: #fff;
}

.pad-10 {
  padding: 0 10px;

}

.jus-evn {
  justify-content: space-evenly;
}

.el-message--error {
  background-color: #ee0a24 !important;
  border-color: #ee0a24 !important;
}

.el-message {
  top: 0 !important;
  min-width: 100% !important;
  padding: 10px 0 !important;
  border-radius: 0px !important;
  justify-content: space-evenly;
}

.el-message .el-icon-error {
  display: none !important;
}

.el-message--error .el-message__content {
  color: #fff !important;

}

.el-message--success {
  background-color: #07c160 !important;
  border-color: #07c160 !important;
}

.el-message .el-icon-success {
  display: none;
}

.el-message--success .el-message__content {
  color: #fff !important;
}

.el-message--warning {
  background-color: #ff976a !important;
  border-color: #ff976a !important
}

.el-message .el-icon-warning {
  display: none;
}

.el-message--warning .el-message__content {
  color: #fff !important;
}
</style>
