<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ include file="../includes/header.jsp" %>
<%--
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.memberVO.username" var="id" />
</sec:authorize>
--%>
		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">
			<!-- Main Content -->
			<div id="content">
				<!-- Topbar -->
				<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">
                        <div class="topbar-divider d-none d-sm-block"></div>
                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                 <span class="mr-2 d-none d-lg-inline text-gray-600 small">
                                    <%-- <c:out value="${id}"/> --%>
                                    <%-- <sec:authentication property="principal.memberVO.korName" /> --%> 님~ 반가워요
                                 </span>
                                <!--
                                <img class="img-profile rounded-circle" src="img/undraw_profile.svg">
                                -->
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="/member">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Profile
                                </a>
                                <!--
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Settings
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Activity Log
                                </a>
                                -->
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="" data-toggle="modal" data-target="#logoutModal">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Logout
                                </a>
                            </div>
                        </li>
                    </ul>
				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">
					    게시판 목록
                    </h1>
                    <div>
                        <a href="/board/register" class="btn btn-primary">작성하기</a>
                    </div>

					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>#번호</th>
                                            <th>제목</th>
                                            <th>작성자</th>
                                            <th>작성일</th>
                                            <th>수정일</th>
                                        </tr>
                                    </thead>
                                    <!--
                                    <tfoot>
                                        <tr>
                                            <th>#번호</th>
                                            <th>제목</th>
                                            <th>작성자</th>
                                            <th>작성일</th>
                                            <th>수정일</th>
                                        </tr>
                                    </tfoot>
                                    -->
                                    <tbody>
									    <c:forEach items="${boardList}" var="board">
											<tr>
												<td><a class='move' href='/board/get?bno=<c:out value="${board.bno}"/>'><c:out value="${board.bno}" /></a></td>
												<td><c:out value="${board.title}" /></td>
												<td><c:out value="${board.writer}" /></td>
												<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}" /></td>
												<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}" /></td>
											</tr>
									    </c:forEach>
                                    </tbody>
								</table>
								<div style="display: flex; align-items: center; justify-content: center;">
									<div style="margin-right: 10px;">검색</div>
									<div>
										<form id='searchForm' action='/board/list' method='get'>
											<select name='type'>
												<option value="">--</option>
												<option value="T" <c:out value="${criteria.type == 'T' ? 'selected' : ''}" />>제목</option>
												<option value="C" <c:if test="${MemberSearch.find_Role == 'C'}">selected</c:if>>내용</option>
												<option value="W">작성자</option>
												<option value="TC">제목 or 내용</option>
												<option value="TW">제목 or 작성자</option>
												<option value="WC">작성자 or 내용</option>
												<option value="TWC">제목 or 내용 or 작성자</option>
											</select>
											<input type='text' name='keyword' value='<c:out value="${criteria.keyword}" />' />
											<%-- <input type='hidden' name='pageNum' />
											<input type='hidden' name='amount' /> --%>
											<button class='btn btn-primary'>Search</button>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- End of Main Content -->


<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="myModalLabel">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">처리가 완료되었습니다.</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
        <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">

</script>

<%@ include file="../includes/footer.jsp" %>