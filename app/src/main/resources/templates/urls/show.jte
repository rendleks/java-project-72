@import hexlet.code.dto.UrlPage
@param UrlPage page

@template.layout.page(
content = @`
    <div class="max-w-7xl mx-auto my-10 px-6 font-sans">
        <h1 class="text-4xl font-normal text-gray-950 mb-6 tracking-tight">
            Сайт: ${page.getUrl().getName()}
        </h1>
        <div class="overflow-x-auto m-10 rounded-md">
            <table data-test="urls" class="min-w-full bg-white border border-gray-300">
                <tbody>
                    <tr class="bg-zing-100">
                        <td class="px-4 py-2 text-left">ID</td>
                        <td class="px-4 py-2 text-left">${page.getUrl().getId()}</td>
                    </tr>
                    <tr class="bg-zing-300">
                        <td class="px-4 py-2 text-left">Имя</td>
                        <td class="px-4 py-2 text-left">${page.getUrl().getName()}</td>
                    </tr>
                    <tr class="bg-zing-100">
                        <td class="px-4 py-2 text-left">Дата создания</td>
                        <td class="px-4 py-2 text-left">${page.getUrl().getCreatedAt()}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
`
)