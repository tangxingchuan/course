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
    name: 'big-file',
    props: {
        text:{
            default:"上传大文件"
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
        use:{
            default:" "
        }

    },
    data: function () {
      return {

      }
    },
    methods: {

        uploadFile () {

            let formData = new window.FormData();
            let file = this.$refs.file.files[0];

            //生成文件标识，标识多次上传的是不是同一个文件
            let key = hex_md5(file);
            let key10=parseInt(key,16);
            let key62=Tool._10to62(key10);
            console.log(key,key10,key62);
            //key比key62长，62进制是，26个大写字母，26个小写字母，10个数字，所以我们上面转成key62


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

            //文件分片
            let shardSize=20 * 1024 * 1024; //以20MB为一个分片
            let shardIndex = 2;		//分片索引，1表示第1个分片
            let start = (shardIndex - 1) * shardSize;	//当前分片起始位置
            let end = Math.min(file.size,start+shardSize); //分片结束的位置
            let fileShard = file.slice(start,end); //从文件中截取当前分片数据

            let size = file.size;
            let shardTotal = Math.ceil(size / shardSize);//总片数

            // key："file"必须和后端controller参数名一致
            formData.append('shard', fileShard);
            formData.append('use', this.use);
            formData.append('shardIndex',shardIndex); //分片索引
            formData.append('shardSize', shardSize); //以20MB为一个分片
            formData.append('shardTotal',shardTotal);//总片数
            formData.append('name', file.name);
            formData.append('suffix', suffix);
            formData.append('size', size);
            formData.append('key', key62);


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
