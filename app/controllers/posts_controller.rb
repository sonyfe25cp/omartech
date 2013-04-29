# -*- encoding: utf-8 -*-
class PostsController < BaseController

  def index
    #@posts = Post.all(:order => [:id.desc]).page(params[:page]).per(params[:limit])
    @posts = Post.all
    respond_to do |format|
      format.html
      format.json {render json: @posts}
    end
  end

  def show
    @post = Post.get(params[:id])
    respond_to do |format|
      format.html
      format.json {render json: @post}
    end
  end

  def new
    @post = Post.new
  end

  def create
    post = Post.new(params[:post])

    respond_to do |format|
      if post.save
        format.html
      else
        redirect_to posts_path
      end
    end
  end

  def review
    status = params[:status]
    post_id = params[:post_id]
    post = Post.get(post_id)

    post.status = status

    post.update(:status => status)

  end

end
