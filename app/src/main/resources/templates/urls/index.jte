@import hexlet.code.dto.UrlsPage
@param UrlsPage page

@template.layout.page(
content = @`
    <div class="max-w-7xl mx-auto my-10 px-6 font-sans">
        <h1 class="text-4xl font-normal text-gray-950 mb-6 tracking-tight">
            Сайты
        </h1>
        <table data-test="urls">
            <thead>
            <tr>
                <th>ID</th>
                <th>Имя</th>
                <th>Дата создания</th>
            </tr>
            </thead>
            <tbody>
                <tr>
                @for(var url: page.getUrls())
                    <td>${url.getId()}</td>
                    <td><a href="/urls/${url.getId()}">${url.getName()}</a></td>
                    <td></td>
                </tr>
                @endfor
            </tbody>
        </table>
    </div>
`
)