# -*- encoding: utf-8 -*-
class User::CommentsController < User::BaseController
  
  def index
    post_id = params[:post_id]
    @comments = Comments.search(:post_id => post_id).all(:id => desc)

    respond_to do |format|
      format.json {render json: @comments}
    end
  end

  def create
    comment = Comment.new(params[:comment])
    comment.save
    redirect_to posts_path(params[:comment][:post_id])
  end
end
