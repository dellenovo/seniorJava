package com.lifei.goods.controller;

import com.github.pagehelper.Page;
import com.lifei.entity.Result;
import com.lifei.entity.StatusCode;
import com.lifei.goods.pojo.Brand;
import com.lifei.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result findAll() {
        List<Brand> brandList = brandService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", brandList);
    }

    /**
     * 根据ID查询品牌数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        Brand brand = brandService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", brand);
    }

    @PostMapping
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable Integer id) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        brandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping("/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Brand> list = brandService.findList(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 分页搜索实现
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result findPage(@PathVariable int page, @PathVariable int size) {
        Page<Brand> pageList = brandService.findPage(page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @GetMapping("/consearch/{page}/{size}")
    public Result findPage(@RequestParam Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Brand> pageList = brandService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }
}
