<form action="/admin/articles/add" method="post">
    <table>
        <tr>
            <td>内容</td>
            <td>
                <textarea name="content" cols="30" rows="15"></textarea>
            </td>
        </tr>
        <tr>
            <td>类型</td>
            <td>
                <select name="type">
                    <option value="Xiaohua">笑话</option>
                    <option value="Bican">比惨</option>
                </select>
                <select name="batch">
                    <option>空</option>
                    <option value="batch">Batch</option>
                </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="添加"></td>
        </tr>
    </table>
</form>