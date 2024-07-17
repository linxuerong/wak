<template>
    <div class="container" ref="scrollref" :class="isScroll ? 'scroll' : ''">
        <span>{{ msg }}</span>
    </div>
</template>
<script>
export default {
    props: {
        msg: {
            default: ''
        },
        Dkey: {
            default: 0
        }
    },
    data () {
        return {

            isScroll: false,
        }
    },
    watch: {
        Dkey: {
            handler (newVal, oldVal) {
                console.log(newVal, oldVal, '95656');
                this.isScroll = true
                setTimeout(() => {
                    this.isScroll = false
                }, 2000);
            },
        }
    },

}
</script>
<style lang="scss" >
.container {
    transition: all 0.5s ease;
    background-color: #ccc;
    text-align: center;
    height: 35px;
    text-align: center;
    line-height: 35px;
    color: #fff;
    font-size: 14px;
    background-color: #ee0a24;
    transform: translateY(-35px);
    position: fixed;
    top: 0;
    width: 100vw;
    z-index: 9999;
}

.scroll {
    transition: all 0.5s ease;
    transform: translateY(0) !important;
}
</style>