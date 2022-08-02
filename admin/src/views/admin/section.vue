<template>
  <div>
      <h4 class="lighter">
          <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
          <router-link to="/business/course" class="pink"> {{course.name}} </router-link>：
          <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
          <router-link to="/business/chapter" class="pink"> {{chapter.name}} </router-link>
      </h4>
      <hr>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>标题</th>
        <th>VOD</th>
        <th>时长</th>
        <th>收费</th>
        <th>顺序</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="section in sections">
          <td>{{section.id}}</td>
          <td>{{section.title}}</td>
          <td>{{section.vod}}</td>
          <td>{{section.time | formatSecond }}</td>
          <td>{{CHARGE | optionKV(section.charge) }}</td>
          <td>{{section.sort}}</td>
      <td>
          <button v-on:click="play(section)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-video-camera bigger-120"></i>
          </button>
        <div class="hidden-sm hidden-xs btn-group">
          <button v-on:click="edit(section)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          <button v-on:click="del(section.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>
      </td>
      </tr>
      </tbody>
    </table>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">标题</label>
                <div class="col-sm-10">
                  <input v-model="section.title" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程</label>
                <div class="col-sm-10">
                    <p class="form-control-static">{{course.name}}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">大章</label>
                <div class="col-sm-10">
                    <p class="form-control-static">{{chapter.name}}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">视频</label>
                <div class="col-sm-10">
                    <!--在file组件中，和组件不相关的业务代码应该由外部通过回调函数传进来。afterUpload()就是我们的外部回调函数-->
                    <vod v-bind:suffixs="['mp4', 'jpeg', 'png']"
                          v-bind:input-id="'video-upload'"
                          v-bind:text="'上传大视频'"
                          v-bind:after-upload="afterUpload"
                          v-bind:use="FILE_USE.COURSE.key"></vod>
                    <div v-show="course.image" class="row">
                        <div class="col-md-9">
                            <player v-bind:player-id=" 'form-plater-div' " ref="player"></player>
                            <video  v-bind:src="section.video" class="hidden" content="controls" id="video"></video>
                        </div>
                    </div>
                </div>
              </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">视频</label>
                    <div class="col-sm-10">
                        <input v-model="section.video | formatSecond" class="form-control" disabled>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">VOD</label>
                    <div class="col-sm-10">
                        <input v-model="section.vod | formatSecond" class="form-control" disabled>
                    </div>
                </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <input v-model="section.time | formatSecond" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                    <select v-model="section.charge" class="form-control">
                        <option v-for="o in CHARGE" v-bind:value="o.key"> {{o.value}}</option>
                    </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input v-model="section.sort" class="form-control">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
  import axios from "axios";
  import Pagination from "../../components/pagination";
  import vod from "../../components/vod";
  import Player from "../../components/player";


  export default {
    components: {Player, vod, Pagination},
    name: "business-section",
    data: function() {
      return {
            section: {},
            sections: [],
            CHARGE:CHARGE,
            FILE_USE:FILE_USE,
            course: {},
            chapter: {},
      }
    },
    mounted: function() {

       this.$refs.pagination.size = 5;
        let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
        let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
             if (Tool.isEmpty(course) || Tool.isEmpty(chapter)) {
                   this.$router.push("/welcome");
                }
        this.course = course;
        this.chapter = chapter;
        this.list(1);
      // sidebar激活样式方法一
        this.$parent.activeSidebar("business-course-sidebar");


    },
    methods: {
      /**
       * 点击【新增】
       */
      add() {

         this.section = {};
        $("#form-modal").modal("show");
      },

      /**
       * 点击【编辑】
       */
      edit(section) {

         this.section = $.extend({}, section);
        $("#form-modal").modal("show");
      },

      /**
       * 列表查询
       */
      list(page) {

        Loading.show();
         axios.post(process.env.VUE_APP_SERVER + '/business/admin/section/list', {
          page: page,
          size:  this.$refs.pagination.size,
         courseId: this.course.id,
         chapterId: this.chapter.id

        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
           this.sections = resp.content.list;
           this.$refs.pagination.render(page, resp.content.total);

        })
      },

      /**
       * 点击【保存】
       */
      save() {


        // 保存校验
        if (1 != 1
          || !Validator.require( this.section.title, "标题")
          || !Validator.length( this.section.title, "标题", 1, 50)
          || !Validator.length( this.section.video, "视频", 1, 200)
        ) {
          return;
        }
          this.section.courseId = this.course.id;
          this.section.chapterId = this.chapter.id;
          this.section.video='';

        Loading.show();
        axios.post(process.env.VUE_APP_SERVER + '/business/admin/section/save',  this.section).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#form-modal").modal("hide");
             this.list(1);
            Toast.success("保存成功！");
          } else {
            Toast.warning(resp.message)
          }
        })
      },

      /**
       * 点击【删除】
       */
      del(id) {

          Confirm.show("删除小节后不可恢复，确认删除？", function () {
          Loading.show();
          axios.delete(process.env.VUE_APP_SERVER + '/business/admin/section/delete/' + id).then((response)=>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
               this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      },

        //在file组件中，和组件不相关的业务代码应该由外部通过回调函数传进来。
        afterUpload(resp){

            let video = resp.content.path;
            let vod =resp.content.vod;
            this.section. video= video;
            this.section. vod= vod;
            this.$forceUpdate();
            this.getTime();
            this.$refs.player.playUrl(video);
        },

        /*
        * 自动获取时长
        * */
        getTime(){
            setTimeout(function () {
                let ele =document.getElementById('video');

                this.section.time = parseInt(ele.duration,10);
            },1000)

        },

        /**
         *
         */

        play(section){
            this.$refs.modalPlayer.playVod(section.vod);
        }
    }
  }
</script>

<style scoped>
    video{
        width: 100%;
        height: auto;
        margin-top: 10px;
    }

</style>
