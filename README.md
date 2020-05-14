# ITMO_RobotSystem_lab8
<ul>
<li><h4 align=center> <a href="Server">Server module</a> </h4></li>
<li><h4 align=center> <a href="Client">Client module</a> </h4></li>
<li><h4 align=center> <a href="Spamer">Spam-Client</a> </h4></li>
</ul>
<hr>
<h2>Общее описание:</h2>


<hr>
<h2>Многопоточность:</h2>
<ul>
<li>Для многопотчной обработки полученного запроса использьзуется <u>Fixed thread pool</u>.</li>
<li>Для многопоточной отправки ответа используется <u>Fixed thread pool</u>.</li>
<li>Для синхронизации доступа к коллекции используется синхронизация чтения и записи с помощью <u>java.util.concurrent.locks.ReadWriteLock</u>.</li>
</ul>
<hr>
<h2>Взаимодействие с почтовым сервером:</h2>
<ul>
<li>Режим работы почтового модуля устанавивается в <a href="Server/src/main/resources/config.properties">.properties</a> файле.</li>
<li>Отправка письма происходит при регистрации нового пользователя на указанный почтовый адрес.</li>
<li>Письмо, содержащее логин и проверочный код, формируется из <a href="Server/src/main/resources/emailTemplate.html"/>html</a> шаблона</li>
</ul>