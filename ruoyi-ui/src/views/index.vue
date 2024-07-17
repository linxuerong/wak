<template>
  <div class="app-container home">
    <div v-if="inviteShow"><img :src="invitePicture" alt="二维码图片"></div>
  </div>
</template>

<script>

import {getQrCode, listUser} from "@/api/system/user";
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.7",
      invitePicture:undefined,
      inviteShow:false
    };
  },
  created() {
    this.getQrcode();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    getQrcode(){
      getQrCode().then(response => {
        const blob = new Blob([response]);
        console.log(blob);
        let src = window.URL.createObjectURL(blob);
        if (blob.size!=0){
          this.inviteShow = true
          this.invitePicture=src;
        }
        console.log(src);
        // this.invitePicture = 'data:image/jpg;base64,'+response
        // this.inviteShow = true
        }
      );
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

