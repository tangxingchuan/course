<template>
   <div>

       <!--按钮-->
       <p>
           <button  @click="add" class="btn btn-white btn-default btn-round">
               <i class="ace-icon fa fa-edit red2"></i>
               新增
           </button>
            &nbsp;
           <button  @click="list(1)" class="btn btn-white btn-default btn-round">
               <i class="ace-icon fa fa-refresh red2"></i>
               刷新
           </button>
       </p>

        <!--分页-->
        <pagination ref="pagination" v-bind:list="list" ></pagination>

       <!--表格-->
       <table id="simple-table" class="table  table-bordered table-hover">
           <thead>
           <tr>
               <th>ID</th>
               <th>名称</th>
               <th>课程ID</th>
               <th>操作</th>
           </tr>
           </thead>

           <tbody>
           <tr v-for="chapter in chapters" :key="chapter">

               <td>{{chapter.id}}</td>

               <td>{{chapter.name}}</td>

               <td>{{chapter.courseId}}</td>
               <td>
                   <div class="hidden-sm hidden-xs btn-group">

                       <button @click="edit(chapter)" class="btn btn-xs btn-info">
                           <i class="ace-icon fa fa-pencil bigger-120"></i>
                       </button>

                       <button @click="dele(chapter.id)" class="btn btn-xs btn-danger">
                           <i class="ace-icon fa fa-trash-o bigger-120"></i>
                       </button>
                   </div>
               </td>

           </tr>

           </tbody>
       </table>

       <!--模态框和表单-->
       <div  id="from-modal" class="modal fade" tabindex="-1" role="dialog">
           <div class="modal-dialog" role="document">
               <div class="modal-content">
                   <div class="modal-header">
                       <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                       <h4 class="modal-title">表单</h4>
                   </div>
                   <div class="modal-body">
                       <form class="form-horizontal">
                           <div class="form-group">
                               <label   class="col-sm-2 control-label">名称</label>
                               <div class="col-sm-10">
                                   <input v-model="chapter.name" type="text" class="form-control"  placeholder="名称">
                               </div>
                           </div>
                           <div class="form-group">
                               <label  class="col-sm-2 control-label">课程ID</label>
                               <div class="col-sm-10">
                                   <input v-model="chapter.courseId"  type="text" class="form-control"  placeholder="课程ID">
                               </div>
                           </div>

                       </form>
                   </div>
                   <div class="modal-footer">
                       <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                       <button @click="save" type="button" class="btn btn-primary">保存</button>
                   </div>
               </div><!-- /.modal-content -->
           </div><!-- /.modal-dialog -->
       </div><!-- /.modal -->

   </div>

</template>

<script>

    import axios from "axios";
    import pagination from "../../components/pagination";


    export default {
        name: "chapter",
        components: {pagination},
        data(){
          return{
              chapter:{},
              chapters:[]
          }
        },
        comments:{pagination},
        mounted() {
            //this.$parent.activeSidebar('business-chapter-sidebar')  //在admin页面通过watch监听路由路径，做了一个通用的sidebar菜单激活，解决了这个问题
            this.$refs.pagination.size=5;
            this.list(1);

        },
        methods:{
            add(){
                this.chapter={};
                $("#from-modal").modal("show");
            },

            edit(chapter){
                $("#from-modal").modal("show");
                this.chapter = $.extend({}, chapter);
            },

            list(page){
                Loading.show();
                  axios.post('http://127.0.0.1:9000/business/admin/chapter/list',
                      {
                          page:page,
                          size:this.$refs.pagination.size,
                      }

                  ).then((response)=>{
                      Loading.hide();
                    console.log('查询大章列表结果',response)
                    this.chapters=response.data.content.list;
                    this.$refs.pagination.render(page,response.data.content.total)
                })
            },

            save(page){

                //保存校验
                if(!Validator.require(this.chapter.name,"名称")
                    || !Validator.require(this.chapter.courseId,'课程ID')
                    ||  !Validator.length(this.chapter.courseId,'课程ID',1,10)){

                    return;
                }


                axios.post('http://127.0.0.1:9000/business/admin/chapter/save',this.chapter).then((response)=>{
                    Loading.hide();
                    console.log('保存课程名字和id',response);
                    if (response.data.success){
                        $("#from-modal").modal("hide");
                        this.list(1);
                        Toast.success('保存成功')
                    }else {
                        Toast.warning(response.chapter.message)
                    }

                })
            },

            dele(id){
                Swal.fire({
                    title: '确认删除？',
                    text: "您将无法还原此内容!",
                    icon: '警告',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '确认!'
                }).then((result) => {
                    if (result.isConfirmed) {
                        Loading.hide();
                        axios.delete('http://127.0.0.1:9000/business/admin/chapter/delete/'+id).then((response)=>{
                            Loading.hide();
                            console.log('删除大章列表结果',response);
                            if (response.data.success){

                                this.list(1);
                                Toast.success('删除成功！')


                            }

                        });
                    }
                });

            },


        }

    }
</script>

<style scoped>

</style>

