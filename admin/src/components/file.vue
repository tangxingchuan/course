<template>
 <div>
     <button type="button"  v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
         <i class="ace-icon fa fa-upload"></i>
         {{text}}
     </button>
     <input  class="hidden" type="file" ref="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
 </div>
</template>

<script>
  import axios from "axios";

  export default {
    name: 'flie',
    props: {
        text:{
            default:"上传文件"
        },
        inputId:{
            default:"file-upload"
        },
        afterUpload:{
            type:Function,
            default: null,
        },
        suffixs:{
            default:[]
        },

    },
    data: function () {
      return {

      }
    },
    methods: {

        uploadFile () {

            let formData = new window.FormData();
            let file = this.$refs.file.files[0];

            // 判断文件格式
            let suffixs = this.suffixs;
            let fileName = file.name;
            let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
            let validateSuffix = false;
            for (let i = 0; i < suffixs.length; i++) {
                if (suffixs[i].toLowerCase() === suffix) {
                    validateSuffix = true;
                    break;
                }
            }
            if (! validateSuffix) {
                Toast.warning("文件格式不正确！只支持上传：" + suffixs.join(","));
                $("#"+this.inputId+"-input").val("");
                return;
            }

            // key："file"必须和后端controller参数名一致
            formData.append('file', file);
            Loading.show();
            axios.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response) => {
                Loading.hide();
                let resp = response.data;
                console.log('上传文件成功',resp);
                //在file组件中，和组件不相关的业务代码应该由外部通过回调函数传进来。afterUpload()就是我们的外部回调函数
                this.afterUpload(resp);
                $("#"+this.inputId+"-input").val("");
            });

        },

        /**
         * 文件上传单独按键
         */
        selectFile(){
            $("#" + this.inputId + "-input").trigger("click");
        }
    }
  }
</script>

<style scoped>

</style>
