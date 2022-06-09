<template>
   <div>

       <h4 class="lighter">
           <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
           <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
       </h4>
       <hr>
       <!--按钮-->
       <p>
           <router-link to="/business/course"  @click="add" class="btn btn-white btn-default btn-round">
               <i class="ace-icon fa fa-arrow-left red2"></i>
               返回课程
           </router-link>
           &nbsp;
           <button  @click="add" class="btn btn-white btn-default btn-round">
               <i class="ace-icon fa fa-edit red2"></i>
               新增
           </button>
            &nbsp;&nbsp;
           <button  @click="list(1)" class="btn btn-white btn-default btn-round">
               <i class="ace-icon fa fa-refresh red2"></i>
               刷新
           </button>
           &nbsp;
       </p>

        <!--分页-->
        <pagination ref="pagination" v-bind:list="list" ></pagination>

       <!--表格-->
       <table id="simple-table" class="table  table-bordered table-hover">
           <thead>
           <tr>
               <th>ID</th>
               <th>名称</th>
               <th>操作</th>
           </tr>
           </thead>


           <tbody>
           <tr v-for="chapter in chapters" :key="chapter">

               <td>{{chapter.id}}</td>

               <td>{{chapter.name}}</td>

               <td>
                   <div class="hidden-sm hidden-xs btn-group">
                       <button v-on:click="toSection(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                           小节
                       </button>&nbsp;
                       <button v-on:click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                           编辑
                       </button>&nbsp;
                       <button v-on:click="dele(chapter.id)" class="btn btn-white btn-xs btn-warning btn-round">
                           删除
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
                               <label  class="col-sm-2 control-label">课程</label>
                               <div class="col-sm-10">
                                   <p class="form-control" >{{course.name}}</p>
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
    import course from "./course";


    export default {
        name: "chapter",
        components: {pagination},
        data(){
          return{
              chapter:{},
              chapters:[],
              course:{},
          }
        },
        mounted() {
            //this.$parent.activeSidebar('business-chapter-sidebar')  //在admin页面通过watch监听路由路径，做了一个通用的sidebar菜单激活，解决了这个问题
            this.$refs.pagination.size=5;
             let course  = SessionStorage.get('course') || {};
             if (Tool.isEmpty(course)){

                 this.$router.push('/welcome')
             }

             this.course=course;
             this.list(1);

        },
        methods:{
            /**
             * 点击【新增】
             */
            add(){
                this.chapter={};
                $("#from-modal").modal("show");
            },
            /**
             * 点击【编辑】
             */
            edit(chapter){
                $("#from-modal").modal("show");
                this.chapter = $.extend({}, chapter);
            },
            /**
             * 列表查询
             */
            list(page){
                Loading.show();
                  axios.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/list',
                      {
                          page:page,
                          size:this.$refs.pagination.size,
                          courseId:this.course.id
                      }

                  ).then((response)=>{
                      Loading.hide();
                    console.log('查询大章列表结果',response)
                    this.chapters=response.data.content.list;
                    this.$refs.pagination.render(page,response.data.content.total)
                })
            },

            /**
             * 点击【保存】
             */
            save(page){

                //保存校验
                if(!Validator.require(this.chapter.name,"名称")
                    ||  !Validator.length(this.chapter.courseId,'课程ID',1,10)){
                    return;
                }

                this.chapter.courseId = this.course.id;

                Loading.show();
                axios.post(process.env.VUE_APP_SERVER +'/business/admin/chapter/save',this.chapter).then((response)=>{
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

            /**
             * 点击【删除】
             */
            dele(id){
                Confirm.show('删除大章后不可恢复，确认删除？',function () {
                    Loading.show();
                    axios.delete(process.env.VUE_APP_SERVER +'/business/admin/chapter/delete/'+id).then((response)=>{
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            this.list(1);
                            Toast.success("删除成功！");
                        }

                    })

                });
            },

            /**
             * 点击【小节】
             */
            toSection(chapter) {
                SessionStorage.set('chapter', chapter);
                this.$router.push("/business/section");
            }

        }

    }
</script>

<style scoped>

</style>

