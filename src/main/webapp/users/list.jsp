<%@ include file="/header.jsp" %>

<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">UserCRUD</h1>
        <a href="/user/add" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                class="fas fa-download fa-sm text-white-50"></i> Dodaj uzytkownika</a>

    </div>

    <!-- Content Row -->
    <div class="row">

        <!-- Content Column -->
        <div class="col-lg-12 mb-4">
            <!-- DataTales Example -->
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Lista uzytkownikow</h6>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Nazwa uzytkownika</th>
                                <th>Email</th>
                                <th>Akcja</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items = "${users}" var="user">
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.userName}</td>
                                    <td>${user.email}</td>
                                    <td>
                                        <a href='<c:url value="/user/delete?id=${user.id}"/>'>Usun</a>
                                        <a href='<c:url value="/user/edit?id=${user.id}"/>'>Edit</a>
                                        <a href='<c:url value="/user/show?id=${user.id}"/>'>Pokaz</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- Nav Item - Dashboard -->
        </div>
    </div>

</div>
<!-- /.container-fluid -->

<%@ include file="/footer.jsp" %>