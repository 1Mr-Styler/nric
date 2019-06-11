<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div class="text-center py-5">
    <div class="container">
        <div class="row my-5 justify-content-center">
            <div class="col-md-9">
                <h1>NRIC Named Entity Recog.</h1>
            <!--<p class="lead text-muted">...</p>-->

                <g:uploadForm controller="main" action="upload">
                    <p>Select NRIC Image</p>
                    <input type="file" class="btn btn-secondary m-2 offset-md-2" name="check"/>

                    <p></p>
                    <g:submitButton name="upload" value="Upload"/>
                </g:uploadForm>
            </div>
        </div>
    </div>
</div>
<g:if test="${flash.hasUpload != null}">
    <div class="py-4 bg-light">
        <div class="container bootstrap snippet">
            <div class="row">
                %{--                <div class="col-sm-10"><h1>User name</h1></div>--}%
                %{--                <div class="col-sm-2"><a href="#" class="pull-right"><img title="profile image" class="img-circle img-responsive" src=""></a></div>--}%
            </div>

            <div class="row">
                <div class="col-sm-3"><!--left col-->


                    <div class="text-center">
                        <g:img src="${flash.image}.png" class="avatar img-circle img-thumbnail" alt="avatar"
                               style="height: 255px;width: 255px"/>
                        %{--                        <h6>Upload a different photo...</h6>--}%
                        %{--                        <input type="file" class="text-center center-block file-upload">--}%
                    </div></hr><br>


                    <div class="panel panel-default">
                        <div class="panel-heading">NRIC <i class="fa fa-link fa-1x"></i></div>

                        <div class="panel-body"><a href="#">${flash.nric}</a></div>
                    </div>


                    %{--<ul class="list-group">
                        <li class="list-group-item text-muted">Activity <i class="fa fa-dashboard fa-1x"></i></li>
                        <li class="list-group-item text-right"><span class="pull-left"><strong>Shares</strong>
                        </span> 125</li>
                        <li class="list-group-item text-right"><span class="pull-left"><strong>Likes</strong></span> 13
                        </li>
                        <li class="list-group-item text-right"><span class="pull-left"><strong>Posts</strong></span> 37
                        </li>
                        <li class="list-group-item text-right"><span class="pull-left"><strong>Followers</strong>
                        </span> 78</li>
                    </ul>

                    <div class="panel panel-default">
                        <div class="panel-heading">Social Media</div>

                        <div class="panel-body">
                            <i class="fa fa-facebook fa-2x"></i> <i class="fa fa-github fa-2x"></i> <i
                                class="fa fa-twitter fa-2x"></i> <i class="fa fa-pinterest fa-2x"></i> <i
                                class="fa fa-google-plus fa-2x"></i>
                        </div>
                    </div>--}%

                </div><!--/col-3-->
                <div class="col-sm-9">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#home">Profile</a></li>
                    </ul>


                    <div class="tab-content">
                        <div class="tab-pane active" id="home">
                            <hr>

                            <form class="form" action="##" method="post" id="registrationForm">
                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="first_name"><h4>Names</h4></label>
                                        <input type="text" class="form-control" name="first_name" id="first_name"
                                               placeholder="first name" value="${flash.names[0]} ${flash.names[1]} ${flash.names[2]}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-6">
                                    </div>
                                </div>

                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="dob"><h4>Date of Birth</h4></label>
                                        <input type="text" class="form-control" name="dob" id="dob"
                                               value="${flash.dob}">
                                    </div>
                                </div>

                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="pob"><h4>Place of Birth</h4></label>
                                        <input type="text" class="form-control" id="pob" value="${flash.pob}">
                                    </div>
                                </div>

                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="gender"><h4>Gender</h4></label>
                                        <input type="text" class="form-control" name="gender" id="gender"
                                               value="${flash.gender}">
                                    </div>
                                </div>

                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="address"><h4>Address</h4></label>
                                        <input type="text" class="form-control" value="${flash.address}" id="address">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <br>
                                        <button class="btn btn-lg btn-success" type="submit"><i
                                                class="glyphicon glyphicon-ok-sign"></i> Save</button>
                                        <button class="btn btn-lg" type="reset"><i
                                                class="glyphicon glyphicon-repeat"></i> Reset</button>
                                    </div>
                                </div>
                            </form>

                            <hr>

                        </div>

                    </div><!--/tab-pane-->
                </div><!--/tab-content-->

            </div><!--/col-9-->
        </div><!--/row-->

    </div>
</g:if>
</body>
</html>
